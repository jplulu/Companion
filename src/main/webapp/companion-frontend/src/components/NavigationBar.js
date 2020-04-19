import React, {Component, Fragment} from "react";
import { Link } from "react-router-dom";
import { connect } from 'react-redux';
import PropTypes from 'prop-types'

// MUI
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

class NavigationBar extends Component {
    render() {
        const {authenticated} = this.props;
        return(
            <AppBar>
                <Toolbar className="nav-container">
                    {authenticated ? (
                        <Fragment>
                            <Button color="inherit" component={Link} to="/">Home</Button>
                            <Button color="inherit" component={Link} to={`/users/${this.props.username}`}>My Profile</Button>
                        </Fragment>
                    ) : (
                        <Fragment>
                            <Button color="inherit" component={Link} to="/login">Login</Button>
                            <Button color="inherit" component={Link} to="/">Home</Button>
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
    username: PropTypes.string.isRequired
};

const mapStateToProps = state => ({
    username: state.user.username,
    authenticated: state.user.authenticated
});

export default connect(mapStateToProps)(NavigationBar);