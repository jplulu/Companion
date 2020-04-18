import React, {Component} from "react";
import axios from'axios';
// Components
import Match from '../components/Match'
import Profile from '../components/Profile'
//MUI
import Grid from '@material-ui/core/Grid';

class home extends Component {
    state = {
        matches: null
    };

    componentDidMount() {
        axios.get("http://localhost:8080/user/2/matches")
            .then(response => {
                this.setState({
                    matches: response.data
                })
            })
            .catch(err => console.log(err.response))
    }

    render() {
        let recentMatchesMarkup = this.state.matches ? (
            this.state.matches.map(match => <Match key={match.id} match={match}/>)
        ) : <p>Loading...</p>;
        return(
            <Grid container spacing={4}>
                <Grid item sm={4} xs={12}>
                    <Profile/>
                </Grid>
                <Grid item sm={8} xs={12}>
                    {recentMatchesMarkup}
                </Grid>
            </Grid>
        )
    }
}

export default home;