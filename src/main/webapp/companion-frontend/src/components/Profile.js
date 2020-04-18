import React, {Component, Fragment} from "react";
import PropTypes from 'prop-types'
import withStyles from "@material-ui/core/styles/withStyles";
import { Link } from 'react-router-dom'
import EditDetails from './EditDetails'

// MUI
import Button from "@material-ui/core/Button";
import Paper from "@material-ui/core/Paper";
import noImage from "../images/no-img.png";
import MuiLink from '@material-ui/core/Link';
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from '@material-ui/core/Tooltip'
// Icons
import LocationOn from '@material-ui/icons/LocationOn';
import EditIcon from '@material-ui/icons/Edit'
import KeyboardReturn from '@material-ui/icons/KeyboardReturn';

//Redux
import { connect } from 'react-redux';
import { logoutUser, uploadImage } from "../redux/actions/userAction";


const styles = (theme) => ({
    ...theme.spreadThis
});

class Profile extends Component {
    handleImageChange = (event) => {
        const image = event.target.files[0];
        const formData = new FormData();
        formData.append('file', image);
        this.props.uploadImage(this.props.user.username, formData);
    };

    handleEditPicture = () => {
        const fileInput = document.getElementById('imageInput');
        fileInput.click();
    };

    handleLogout = () => {
        this.props.logoutUser();
    };

    render() {
        const {classes, user: {authenticated, loading, id, username, profile: {firstName, lastName, age, gender, location, bio, profilePic}}} = this.props;
        const imageURL = (profilePic && profilePic.length) ? `http://localhost:8080/picture?id=${profilePic[profilePic.length-1].id}` : undefined;

        let profileMarkup = !loading ? (authenticated ? (
            <Paper className={classes.paper}>
                <div className={classes.profile}>
                    <div className="image-wrapper">
                        <img src={imageURL ? imageURL : noImage} alt="profile" className="profile-image"/>
                        <input type="file" id="imageInput" hidden="hidden" onChange={this.handleImageChange} />
                        <Tooltip title="Edit profile picture" placement="top">
                            <IconButton onClick={this.handleEditPicture} className="button">
                                <EditIcon color="primary"/>
                            </IconButton>
                        </Tooltip>
                    </div>
                    <hr/>
                    <div className="profile-details">
                        <MuiLink component={Link} to={`/users/${username}`} color="primary" variant="h5">
                            {firstName}
                        </MuiLink>
                        <hr/>
                        {bio && <Typography variant="body2">{bio}</Typography> }
                        <hr/>
                        {location && (
                            <Fragment>
                                <LocationOn color="primary"/> <span>{location}</span>
                                <hr/>
                            </Fragment>
                        )}
                    </div>
                    <Tooltip title="Logout" placement="top">
                        <IconButton onClick={this.handleLogout}>
                            <KeyboardReturn color="primary"/>
                        </IconButton>
                    </Tooltip>
                    <EditDetails/>
                </div>
            </Paper>
        ) : (
            <Paper className={classes.paper}>
                <Typography variant="body2" align="center">
                    No profile found, please login
                </Typography>
                <div className={classes.buttons}>
                    <Button variant="contained" color="primary" component={Link} to="/login">
                        Login
                    </Button>
                    <Button variant="contained" color="secondary" component={Link} to="/signup">
                        Sign up
                    </Button>
                </div>
            </Paper>
        )) : (<p>loading...</p>);
        return profileMarkup;
    }
}

Profile.propTypes = {
    user: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    logoutUser: PropTypes.func.isRequired,
    uploadImage: PropTypes.func.isRequired
};

const mapStateToProps = (state) => ({
    user: state.user
});

const mapActionsToProps = {logoutUser, uploadImage};


export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(Profile))