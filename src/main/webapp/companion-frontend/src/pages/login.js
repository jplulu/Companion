import React, {Component} from "react";
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
import {loginUser} from "../redux/actions/userAction";

const styles = (theme) => ({
    ...theme.spreadThis
});


class login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            errors: {}
        }
    };

    componentWillReceiveProps(nextProps, nextContext) {
        if(nextProps.UI.errors) {
            this.setState({ errors : { general: nextProps.UI.errors.message }});
        }
    }

    validateForm = () => {
        let isError = false;
        const err = {};
        if(this.state.username === "") {
            isError = true;
            err.username = "Must not be empty";
        }
        if(this.state.password === "") {
            isError = true;
            err.password = "Must not be empty";
        }

        this.setState({
            errors: {
                ...err
            }
        });

        return isError
    };

    handleSubmit = (e) => {
        e.preventDefault();
        this.setState({
            errors: {}
        });
        const isError = this.validateForm();
        if(!isError) {
            const userData = {
                username: this.state.username,
                password: this.state.password
            };
            this.props.loginUser(userData, this.props.history)
        }
    };

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    };

    render() {
        const { classes, UI: { loading } } = this.props;
        const { errors } = this.state;
        return(
            <Grid container className={classes.form}>
                <Grid item sm/>
                <Grid item sm>
                    <Typography variant="h2" className={classes.pageTitle}>Login</Typography>
                    <form noValidate onSubmit={this.handleSubmit}>
                        <TextField id="username" name="username" type="username" label="Username" className={classes.textField}
                                   helperText={errors.username} error={!!errors.username} value={this.state.username}
                                   onChange={this.handleChange} fullWidth/>
                        <TextField id="password" name="password" type="password" label="Password" className={classes.textField}
                                   helperText={errors.password} error={!!errors.password} value={this.state.password}
                                   onChange={this.handleChange} fullWidth/>
                        {errors.general && (
                            <Typography variant="body2" className={classes.customError}>
                                {errors.general}
                            </Typography>
                        )}
                        <Button type="submit" variant="contained" color="primary" className={classes.button} disabled={loading}>
                            Login
                            {loading && (
                                <CircularProgress size={30} className={classes.loadSpinner}/>
                            )}
                        </Button>
                        <br/>
                        <small>Dont have an account? sign up <Link to="/signup">here</Link></small>
                    </form>
                </Grid>
                <Grid item sm/>
            </Grid>
        )
    }
}

login.propTypes = {
    classes: PropTypes.object.isRequired,
    loginUser: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    UI: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    user: state.user,
    UI: state.UI
});

const mapActionsToProps = {
    loginUser
};

export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(login));