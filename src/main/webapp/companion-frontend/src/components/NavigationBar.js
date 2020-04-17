import React, {Component} from "react";
import Link from 'react-router-dom/Link'

// MUI
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

class NavigationBar extends Component {
    render() {
        return(
            <AppBar>
                <Toolbar className="nav-container">
                    <Button color="inherit" component={Link} to="/login">Login</Button>
                    <Button color="inherit" component={Link} to="/">Home</Button>
                    <Button color="inherit" component={Link} to="/signup">Sign up</Button>
                </Toolbar>
            </AppBar>
        )
    }
}

export default NavigationBar;