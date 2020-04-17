import React, {Component} from "react";
import axios from'axios';
import Match from '../components/Match'
//MUI
import Grid from '@material-ui/core/Grid';

class home extends Component {
    state = {
        matches: null
    };

    componentDidMount() {
        axios.get("http://localhost:8080/user/2/matches")
            .then(response => {
                console.log(response.data);
                this.setState({
                    matches: response.data
                })
            })
            .catch(err => console.log(err.response))
    }

    render() {
        let recentMatchesMarkup = this.state.matches ? (
            this.state.matches.map(match => <Match match={match}/>)
        ) : <p>Loading...</p>;
        return(
            <Grid container spacing={4}>
                <Grid item sm={4} xs={12}>
                    <p>Profile...</p>
                </Grid>
                <Grid item sm={8} xs={12}>
                    {recentMatchesMarkup}
                </Grid>
            </Grid>
        )
    }
}

export default home;