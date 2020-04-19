import React, {Component} from "react";
import axios from 'axios';
import StaticProfile from '../components/StaticProfile'
import Typography from "@material-ui/core/Typography";

class user extends Component {
    state = {
        userData: null,
        loading:false,
        errors: {}
    };

    componentDidMount() {
        const username = this.props.match.params.username;
        this.getUserData(username);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.match.params.username !== this.props.match.params.username) {
            const username = this.props.match.params.username;
            this.getUserData(username);
        }
    }

    getUserData = (username) => {
        const url = `http://localhost:8080/user/${username}`;
        this.setState({
            loading: true
        });
        axios.get(url)
            .then(res => {
                this.setState({
                    userData: res.data,
                    loading: false
                })
            })
            .catch(() => {
                this.setState({
                    loading: false,
                    errors: {
                        general: "You do not have permission to view this profile"
                    }
                })
            })
    };

    render() {
        const { errors } = this.state;
        if(!this.state.loading) {
            if(this.state.userData) {
                return (
                    <StaticProfile userData={this.state.userData}/>
                );
            } else {
                return (
                    <Typography variant="h3" align="center" color="inherit">
                        {errors.general}
                    </Typography>
                );
            }
        } else {
            return (
                <div>Loading...</div>
            )
        }

    }
}

export default user;