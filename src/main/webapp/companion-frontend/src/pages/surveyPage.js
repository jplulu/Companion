import React, {Component} from "react";
import SurveyQuestion from "../components/SurveyQuestion";
import EditDetails from "../components/EditDetails"
import user from "./user"
import axios from 'axios';
import StaticProfile from '../components/StaticProfile'
import Typography from "@material-ui/core/Typography";
import surveyCheckBox from '../components/survey/surveyCheckBox'


class surveyPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            questions: ["Which of the following sports do you enjoy playing?",
                "Which genres of music do you enjoy listening too?",
                "What types of food do you love?",
                "What hobbies are you interested in?",
                "How would you describe your personality type?",
                "Do you consider yourself an animal lover?"],
            options: {
                sports: [
                    {id: 1, value: "basketball", name: "Basketball", isChecked: false},
                    {id: 2, value: "football", name: "Football", isChecked: false},
                    {id: 3, value: "baseball", name: "Baseball", isChecked: false},
                    {id: 4, value: "volleyball", name: "Volleyball", isChecked: false}
                ],
                music: [
                    {id: 1, value: "rock", name: "Rock", isChecked: false},
                    {id: 2, value: "pop", name: "Pop", isChecked: false},
                    {id: 3, value: "country", name: "Country", isChecked: false},
                    {id: 4, value: "r&b/soul", name: "R&B/Soul", isChecked: false},
                    {id: 5, value: "hiphop", name: "HipHop", isChecked: false},
                    {id: 6, value: "easylistening", name: "Easy Listening", isChecked: false},
                    {id: 7, value: "electronic/dance", name: "Electronic/Dance", isChecked: false}
                ]
            },

            answers: {
                sports: [],
                music: []
            }
        }
    }

    render() {
        return(
            <div>
                <surveyCheckBox question={this.state.questions[0]} options={this.state.options.sports} answers={this.state.answers.sports} />
                <p>This is on main page</p>
            </div>
        )
    }
}
            /*
            userData: 'Hello',
            userProfile: 'Hello',
            loading:false,
            errors: {},
            genderPreference: "None",
            questions: ["sport","food","music","hobby","personalityType","likesAnimals"],
            options:[["Basketball","Swimming","Football","Knitting","Dancing"],[],[],[],[],[]],
            answers: [[false,true,true,true,false],[],[],[],null,null],
            quack: false

        }
        this.handleChangeSelect = this.handleChangeSelect.bind(this)
        this.handleChangeCheck = this.handleChangeCheck(this)
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

    handleChangeCheck(event) {
        const {name, type, checked} = event.target
        this.setState({
            [name]: checked
        })
    }


            render(){
        return(
            <div>

                    <input
                        type="checkbox"
                        name = "quack"
                        onChange = {this.handleChangeCheck}
                        checked={this.state.quack}

                    /> {this.state.answers[0][0]}
                    <br/>












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

                {/*---------------------------------------------------------------------------------------------*/

/*
                <SurveyQuestion
                    type = "check"
                    question = "What sports do you like?"
                    options = {this.state.options[0]}
                    answers = {this.state.answers[0]}


                />
                <hr/>
                {this.state.answers[0][0]}

                {/*---------------------------------------------------------------------------------------------*/
/*
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
*/
export default surveyPage