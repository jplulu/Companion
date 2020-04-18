import React, {Component} from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import PropTypes from 'prop-types';
import {Link} from "react-router-dom";
import axios from'axios';

// MUI
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";


const styles = (theme) => ({
    ...theme.spreadThis
});


class login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            loading: false,
            errors: {}
        }
    };

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
            this.setState({
                loading: true
            });
            const userData = {
                username: this.state.username,
                password: this.state.password
            };
            console.log(userData);
            axios.post('http://localhost:8080/authenticate', userData)
                .then(res => {
                    localStorage.setItem('jwtToken', `Bearer ${res.data.jwt}`);
                    this.setState({
                        loading: false
                    });
                    this.props.history.push('/');
                })
                .catch(err => {
                    console.log(err.response);
                    this.setState({
                        errors: {
                            general: err.response.data.message
                        },
                        loading: false
                    })
                });
        }
    };

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    };

    render() {
        const { classes } = this.props;
        const { errors, loading } = this.state;
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
    classes: PropTypes.object.isRequired
};


export default withStyles(styles)(login);