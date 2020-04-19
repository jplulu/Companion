import React, {Component, Fragment} from "react";
import PropTypes from 'prop-types'
import withStyles from "@material-ui/core/styles/withStyles";
//MUI
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
// Icons
import EditIcon from '@material-ui/icons/Edit';
//Redux
import { connect } from 'react-redux';
import {editUserDetails} from "../redux/actions/userAction";

const styles = (theme) => ({
    ...theme.spreadThis,
    button: {
        float: 'right'
    }
});

class EditDetails extends Component {
    state = {
        firstName: '',
        lastName: '',
        age: 0,
        location: '',
        bio: '',
        open: false
    };

    componentDidMount() {
        const { profile } = this.props;
        this.mapUserDetailsToState(profile);
    }

    handleOpen = () => {
        this.setState({
            open: true
        });
        this.mapUserDetailsToState(this.props.profile);
    };

    handleClose = () => {
        this.setState({ open: false })
    };

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    };

    handleSubmit = () => {
        const userDetails = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            age: this.state.age,
            location: this.state.location,
            bio: this.state.bio
        };
        console.log(userDetails);
        this.props.editUserDetails(this.props.username, userDetails);
        this.handleClose();
    };

    mapUserDetailsToState = (profile) => {
        this.setState({
            firstName: profile.firstName ? profile.firstName : '',
            lastName: profile.lastName ? profile.lastName : '',
            age: profile.age,
            location: profile.location,
            bio: profile.bio ? profile.bio : '',
        });
    };

    render() {
        const { classes } = this.props;
        return (
            <Fragment>
                <Tooltip title="Edit profile" placement="top">
                    <IconButton onClick={this.handleOpen} className={classes.button}>
                        <EditIcon color="primary"/>
                    </IconButton>
                </Tooltip>
                <Dialog open={this.state.open}
                        onClose={this.handleClose}
                        fullWidth
                        maxWidth="sm">
                    <DialogTitle>Edit your profile</DialogTitle>
                    <DialogContent>
                        <form>
                            <TextField
                                name="firstName"
                                type="text"
                                label="First name"
                                placeholder="First name"
                                className={classes.textField}
                                value={this.state.firstName}
                                onChange={this.handleChange}
                                fullWidth
                                />
                            <TextField
                                name="lastName"
                                type="text"
                                label="Last name"
                                placeholder="Last name"
                                className={classes.textField}
                                value={this.state.lastName}
                                onChange={this.handleChange}
                                fullWidth
                            />
                            <TextField
                                name="bio"
                                type="text"
                                label="Bio"
                                multiline
                                rows="3"
                                placeholder="A short bio about yourself"
                                className={classes.textField}
                                value={this.state.bio}
                                onChange={this.handleChange}
                                fullWidth
                            />
                            <TextField
                                name="location"
                                type="text"
                                label="Location (City, State)"
                                placeholder="Where you live"
                                className={classes.textField}
                                value={this.state.location}
                                onChange={this.handleChange}
                                fullWidth
                            />
                        </form>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            Cancel
                        </Button>
                        <Button onClick={this.handleSubmit} color="primary">
                            Save
                        </Button>
                    </DialogActions>
                </Dialog>
            </Fragment>
        )
    }
}

EditDetails.propTypes = {
    editUserDetails: PropTypes.func.isRequired,
    classes: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    username: state.user.username,
    profile: state.user.profile
});

export default connect(mapStateToProps, { editUserDetails })(withStyles(styles)(EditDetails))