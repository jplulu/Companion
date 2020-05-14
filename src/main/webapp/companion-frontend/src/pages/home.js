import React, {Component} from "react";
import {connect} from "react-redux";
// Components
import Match from '../components/Match'
import Profile from '../components/Profile'
//MUI
import Grid from '@material-ui/core/Grid';
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";
import Typography from "@material-ui/core/Typography";

const styles = (theme) => ({
    ...theme.spreadThis
});

class home extends Component {

    render() {
        const {user: {username, authenticated, matches, loadingMatches}} = this.props;
        if(authenticated) {
            let recentMatchesMarkup = !loadingMatches ? ((matches && matches.length) ? (
                matches.map(match => <Match key={match.id} curUsername={username} match={match}/>)
            ) : <h2>No Matches Found. Try editing your profile or doing the survey</h2>) : (
                <p>Loading...</p>
            );
            return (
                <Grid container spacing={4}>
                    <Grid item sm={4} xs={12}>
                        <Profile/>
                    </Grid>
                    <Grid item sm={8} xs={12}>
                        <Typography variant="h4" color="primary" align="center">
                            Your matches
                        </Typography>
                        <br/>
                        {recentMatchesMarkup}
                    </Grid>
                </Grid>
            )
        } else {
            return (
                <div>
                    <Typography variant="h1" color="primary" align="center">
                        Welcome to Companion
                    </Typography>
                    <Typography variant="h2" color="inherit" align="center">
                        A place to find new friends
                    </Typography>
                </div>
            )
        }
    }
}

home.propTypes = {
    user: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    user: state.user
});

export default connect(mapStateToProps)(withStyles(styles)(home));
