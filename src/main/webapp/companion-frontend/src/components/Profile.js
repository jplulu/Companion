import React, {Component, Fragment} from "react";
import PropTypes from 'prop-types'
import withStyles from "@material-ui/core/styles/withStyles";
import { Link } from 'react-router-dom'

// MUI
import Button from "@material-ui/core/Button";
import Paper from "@material-ui/core/Paper";
import noImage from "../images/no-img.png";
import MuiLink from '@material-ui/core/Link';
import Typography from "@material-ui/core/Typography";
// Icons
import LocationOn from '@material-ui/icons/LocationOn';

//Redux
import { connect } from 'react-redux';


const styles = (theme) => ({
    ...theme.spreadThis
});

class Profile extends Component {
    render() {
        const {classes, user: {authenticated, loading, id, username, profile: {firstName, lastName, age, gender, location, bio, profilePic}}} = this.props;
        const imageURL = (profilePic && profilePic.length) ? `http://localhost:8080/picture?id=${profilePic[0].id}` : undefined;

        let profileMarkup = !loading ? (authenticated ? (
            <Paper className={classes.paper}>
                <div className={classes.profile}>
                    <div className="image-wrapper">
                        <img src={imageURL ? imageURL : noImage} alt="profile" className="profile-image"/>
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
    classes: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    user: state.user
});


export default connect(mapStateToProps)(withStyles(styles)(Profile))