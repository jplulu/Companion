import React, {Component} from "react";
import SurveyQuestion from "../components/SurveyQuestion";
import EditDetails from "../components/EditDetails"
import user from "./user"
import axios from 'axios';
import StaticProfile from '../components/StaticProfile'
import Typography from "@material-ui/core/Typography";

class surveyPage extends Component{
    constructor(props){
        super(props);
        this.state = {
            userData: 'Hello',
            userProfile: 'Hello',
            loading:false,
            errors: {},
            genderPreference: "None",
            questions: ["sport","food","music","hobby","personalityType","likesAnimals"],
            options:[["Basketball","Swimming","Football","Knitting","Dancing"],[],[],[],[],[]],
            answers: [[1,0,1,1],[],[],[],null,null]
        }
        this.handleChangeSelect = this.handleChangeSelect.bind(this)

    }

    componentDidMount() {
        const username = this.props.match.params.username;
        this.getUserData(username);
        this.getUserSurveyResults(username)
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
                    userProfile: res.data.profile,
                    loading: false
                })
            })
            .catch(() => {
                this.setState({
                    userData: 'goodbye',
                    loading: false,
                    errors: {
                        general: "You do not have permission to view this profile"
                    },
                })
            })
    };

    getUserSurveyResults = (username) => {

    }

    handleChangeSelect(event){
        const {name, value, type, checked} = event.target
        this.setState({
            [name]: value
        })
    }


    render(){
        return(
            <div>

                Please select what gender individuals you would be interested in matching with:
                <br/>
                <select
                    value = {this.state.genderPreference}
                    name = "genderPreference"
                    onChange = {this.handleChangeSelect}
                >
                    <option value="">-- Please Choose a preference --</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="none">No Preference</option>

                </select>

                <h1>{this.state.genderPreference}</h1>

                <SurveyQuestion
                    type = "select"
                    default = "-- Please Select an Option --"
                    question = "What is your gender preference?"
                    options = {["male","female","no preference"]}
                />

                <SurveyQuestion
                    type = "select"
                    default = "-- Please Select an Option --"
                    question = "What age range are you interested in meeting?"
                    options = {["Basketball","Swimming","Football","Knitting","Dancing"]}
                />
                <hr/>

                {/*---------------------------------------------------------------------------------------------*/}


                <SurveyQuestion
                    type = "check"
                    question = "What sports do you like?"
                    options = {this.state.options[0]}
                    answers = {this.state.answers[0]}


                />
                <hr/>
                {this.state.answers[0][0]}

                {/*---------------------------------------------------------------------------------------------*/}

                <hr/>
                HELLO WORLD
                <hr/>
                username:  {this.state.userData.username} <br/>
                id: {this.state.userData.id} <br/>
                profile:  {this.state.userData.password} <br/>
                name from profile: {this.state.userProfile.firstName}

                <EditDetails/>
                <form>Hello</form>
            </div>
        )
    }


}

export default surveyPage