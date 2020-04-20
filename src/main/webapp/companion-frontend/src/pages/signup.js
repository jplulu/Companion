import React, {Component, Fragment} from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import PropTypes from 'prop-types';
import {Link} from "react-router-dom";
// MUI
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
// Redux
import { connect } from 'react-redux'
import { signupUser } from "../redux/actions/userAction";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";

const styles = (theme) => ({
    ...theme.spreadThis
});


class signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            confirmPassword: '',

            firstName: '',
            lastName: '',
            bio: '',
            location: '',
            age: '',
            gender: '',

            errors: {}
        }
    };

    componentWillReceiveProps(nextProps, nextContext) {
        if(nextProps.UI.errors) {
            this.setState({ errors : { general: nextProps.UI.errors.message }});
        }
    }

    validateForm = () => {
        const {username, password, confirmPassword} = this.state;
        let isError = false;
        const err = {};
        if(username === "") {
            isError = true;
            err.username = "Must not be empty";
        }
        else if(username.length < 5) {
            isError = true;
            err.username = "Needs to be at least 5 characters long";
        }
        if(password === "") {
            isError = true;
            err.password = "Must not be empty";
        }
        else if(password.length < 5) {
            isError = true;
            err.password = "Needs to be at least 5 characters long";
        }
        if(password !== confirmPassword) {
            isError = true;
            err.confirmPassword = "Passwords do not match "
        }

        this.setState({
            errors: {
                ...err
            }
        });

        return isError
    };

    handleCredentialsSubmit = (e) => {
        e.preventDefault();
        this.setState({
            errors: {}
        });
        const isError = this.validateForm();
        if(!isError) {
            const newUserData = {
                username: this.state.username,
                password: this.state.password
            };
            this.props.signupUser(newUserData, this.props.history);
        }
    };

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    };

    render() {
        const { classes, UI: { loading } } = this.props;
        const { errors, page } = this.state;
        return (
            <Grid container className={classes.form}>
                <Grid item sm/>
                <Grid item sm>
                    <Typography variant="h2" className={classes.pageTitle}>Create Account</Typography>
                    <form noValidate onSubmit={this.handleCredentialsSubmit}>
                        <TextField id="username" name="username" type="username" label="Username"
                                   className={classes.textField}
                                   helperText={errors.username} error={!!errors.username} value={this.state.username}
                                   onChange={this.handleChange} fullWidth/>
                        <TextField id="password" name="password" type="password" label="Password"
                                   className={classes.textField}
                                   helperText={errors.password} error={!!errors.password} value={this.state.password}
                                   onChange={this.handleChange} fullWidth/>
                        <TextField id="confirmPassword" name="confirmPassword" type="password" label="Confirm Password"
                                   className={classes.textField}
                                   helperText={errors.confirmPassword} error={!!errors.confirmPassword}
                                   value={this.state.confirmPassword}
                                   onChange={this.handleChange} fullWidth/>
                        {errors.general && (
                            <Typography variant="body2" className={classes.customError}>
                                {errors.general}
                            </Typography>
                        )}
                        <Button type="submit" variant="contained" color="primary" className={classes.button} disabled={loading} >
                            Sign up
                            {loading && (
                                <CircularProgress size={30} className={classes.loadSpinner}/>
                            )}
                        </Button>
                        <br/>
                        <small>Already have an account? Login <Link to="/login">here</Link></small>
                    </form>
                </Grid>
                <Grid item sm/>
            </Grid>
        )
    }
}

signup.propTypes = {
    classes: PropTypes.object.isRequired,
    signupUser: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    UI: PropTypes.object.isRequired
};


const mapStateToProps = (state) => ({
    user: state.user,
    UI: state.UI
});

const mapActionsToProps = {
    signupUser,
};

export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(signup));