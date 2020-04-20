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
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormControl from "@material-ui/core/FormControl";
// Redux
import { connect } from 'react-redux'
import { signupUser } from "../redux/actions/userAction";
import {FormLabel} from "@material-ui/core";
import RadioGroup from "@material-ui/core/RadioGroup";
import Radio from "@material-ui/core/Radio";
import FormHelperText from "@material-ui/core/FormHelperText";


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
            age: '',
            gender: '',
            bio: '',
            location: '',

            page: 'PROFILE',
            errors: {}
        }
    };

    componentWillReceiveProps(nextProps, nextContext) {
        if(nextProps.UI.errors) {
            this.setState({
                ...this.state,
                page: 'CREDENTIAL',
                errors: {general: nextProps.UI.errors.message}
            });
        }
    }

    validateCredentialForm = () => {
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

    handleCredentialSubmit = (e) => {
        e.preventDefault();
        this.setState({
            errors: {}
        });
        const isError = this.validateCredentialForm();
        if(!isError) {
            const newUserData = {
                username: this.state.username,
                password: this.state.password
            };
            this.props.signupUser(newUserData, this.props.history);
            if(!this.state.errors.general) {
                this.setState({
                    ...this.state,
                    page: 'PROFILE'
                })
            }
        }
    };

    validateProfileForm = () => {
        const {firstName, lastName, age, gender, location} = this.state;
        let isError = false;
        const err = {};
        if(firstName === "") {
            isError = true;
            err.firstName = "Must not be empty";
        }
        if(lastName === "") {
            isError = true;
            err.lastName = "Must not be empty";
        }
        if(age === "") {
            isError = true;
            err.age = "Must not be empty";
        } else {
            const testAge = parseInt(age, 10);
            if (isNaN(testAge) || testAge < 1 || testAge > 100) {
                isError = true;
                err.age = "Must be a valid age"
            }
        }
        if(gender === "") {
            isError = true;
            err.gender = "Must not be empty";
        }
        if(location === "") {
            isError = true;
            err.location = "Must not be empty";
        }

        this.setState({
            errors: {
                ...err
            }
        });

        return isError
    };

    handleProfileSubmit= (e) => {
        e.preventDefault();
        this.setState({
            errors: {}
        });
        const isError = this.validateProfileForm();
        if(!isError) {
            const newUserProfile = {
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                age: parseInt(this.state.age),
                gender: parseInt(this.state.gender),
                location: this.state.location,
                bio: this.state.bio
            };
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
        if(page === 'CREDENTIAL') {
            return (
                <Grid container className={classes.form}>
                    <Grid item sm/>
                    <Grid item sm>
                        <Typography variant="h2" className={classes.pageTitle}>Create Account</Typography>
                        <form noValidate onSubmit={this.handleCredentialSubmit}>
                            <TextField id="username" name="username" type="text" label="Username"
                                       className={classes.textField}
                                       helperText={errors.username} error={!!errors.username} value={this.state.username}
                                       onChange={this.handleChange} required fullWidth />
                            <TextField id="password" name="password" type="password" label="Password"
                                       className={classes.textField}
                                       helperText={errors.password} error={!!errors.password} value={this.state.password}
                                       onChange={this.handleChange} fullWidth required autoComplete='off'/>
                            <TextField id="confirmPassword" name="confirmPassword" type="password" label="Confirm Password"
                                       className={classes.textField}
                                       helperText={errors.confirmPassword} error={!!errors.confirmPassword}
                                       value={this.state.confirmPassword}
                                       onChange={this.handleChange} fullWidth autoComplete='off'/>
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
                </Grid>)
            } else if (page === 'PROFILE') {
                return (
                <Grid container className={classes.form}>
                    <Grid item sm/>
                    <Grid item sm>
                        <Typography variant="h2" className={classes.pageTitle}>Set up profile</Typography>
                        <form noValidate onSubmit={this.handleProfileSubmit}>
                            <TextField id="firstName" name="firstName" type="text" label="First Name"
                                       className={classes.textField}
                                       helperText={errors.firstName} error={!!errors.firstName} value={this.state.firstName}
                                       onChange={this.handleChange} fullWidth required autoComplete='off'/>
                            <TextField id="lastName" name="lastName" type="text" label="Last Name"
                                       className={classes.textField}
                                       helperText={errors.lastName} error={!!errors.lastName} value={this.state.lastName}
                                       onChange={this.handleChange} fullWidth required autoComplete='off'/>
                            <TextField id="age" name="age" type="number" label="Age"
                                       className={classes.textField}
                                       helperText={errors.age} error={!!errors.age} value={this.state.age}
                                       onChange={this.handleChange} fullWidth required autoComplete='off'/>
                            <Grid item>
                               <FormControl error={!!errors.gender}>
                                   <FormLabel component="legend">Gender *</FormLabel>
                                   <RadioGroup row name="gender" value={this.state.gender} onChange={this.handleChange}>
                                        <FormControlLabel value="1" control={<Radio />} label="Male"/>
                                        <FormControlLabel value="2" control={<Radio />} label="Female"/>
                                        <FormControlLabel value="3" control={<Radio />} label="Other"/>
                                   </RadioGroup>
                                   <FormHelperText>{errors.gender}</FormHelperText>
                               </FormControl>
                            </Grid>

                            <TextField id="location" name="location" type="text" label="Location (City, State)"
                                       className={classes.textField}
                                       helperText={errors.location} error={!!errors.location} value={this.state.location}
                                       onChange={this.handleChange} fullWidth required autoComplete='off'/>
                            <TextField id="bio" name="bio" type="text" label="Bio"
                                       className={classes.textField}
                                       helperText={errors.bio} error={!!errors.bio}
                                       value={this.state.bio}
                                       onChange={this.handleChange} fullWidth multiline/>
                            <Button type="submit" variant="contained" color="primary" className={classes.button} disabled={loading} >
                                Submit
                                {loading && (
                                    <CircularProgress size={30} className={classes.loadSpinner}/>
                                )}
                            </Button>
                        </form>
                    </Grid>
                    <Grid item sm/>
                </Grid>
                )
            }

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