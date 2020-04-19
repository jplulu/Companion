import React, {Component, Fragment} from "react";
import { Link } from "react-router-dom";
import { connect } from 'react-redux';
import PropTypes from 'prop-types'

// MUI
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import {logoutUser} from "../redux/actions/userAction";
import Typography from "@material-ui/core/Typography";

class NavigationBar extends Component {

    handleLogout = () => {
        this.props.logoutUser();
        window.location.href = 'http://localhost:3000/';
    };

    render() {
        const {authenticated} = this.props;
        return(
            <AppBar>
                <Toolbar>
                    {authenticated ? (
                        <Fragment>
                            <Button color="inherit" component={Link} to="/">Home</Button>
                            <Button color="inherit" component={Link} to={`/users/${this.props.username}`} style={{flex:1}}>My Profile</Button>
                            <Button color="secondary" onClick={this.handleLogout} >Logout</Button>
                        </Fragment>
                    ) : (
                        <Fragment>
                            <Button color="inherit" component={Link} to="/">{<Typography variant="h6">Companion</Typography>}</Button>
                            <Button color="inherit" component={Link} to="/login">Login</Button>
                            <Button color="inherit" component={Link} to="/signup">Sign up</Button>
                        </Fragment>
                    )}
                </Toolbar>
            </AppBar>
        )
    }
}

NavigationBar.propTypes = {
    authenticated: PropTypes.bool.isRequired,
    username: PropTypes.string.isRequired,
    logoutUser: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
    username: state.user.username,
    authenticated: state.user.authenticated
});

export default connect(mapStateToProps, {logoutUser})(NavigationBar);