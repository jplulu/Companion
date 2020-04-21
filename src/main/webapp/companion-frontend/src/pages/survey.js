import React, {Component} from "react";
import SurveyQuestion from "../components/SurveyQuestion";
import EditDetails from "../components/EditDetails"
import user from "./user"
import axios from 'axios';
import StaticProfile from '../components/StaticProfile'
import Typography from "@material-ui/core/Typography";

class survey extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userData: 'Hello',
            userProfile: 'Hello',
            loading: false,
            errors: {},
            genderPreference: "None",
            questions: ["sport", "food", "music", "hobby", "personalityType", "likesAnimals"],
            options: [["Basketball", "Swimming", "Football", "Knitting", "Dancing"], [], [], [], [], []],
            answers: [[false, true, true, true, false], [], [], [], null, null],
            quack: false,
            isLactoseFree: false,
            testing: {
                Sammy: false,
                Baking: false
            }
        }
        this.handleChangeSelect = this.handleChangeSelect.bind(this)
    }

    handleChangeSelect(event) {
        const{name,value,type,checked}=event.target
        this.setState({
            [name]: checked
        })
    }

    render(){
        return(
            <div>
                <label>
                    <input
                        type="checkbox"
                        name="isLactoseFree"
                        onChange={this.handleChangeSelect}
                        checked={this.state.isLactoseFree}
                    /> Lactose Free?
                </label>
                <br/>
                {this.state.isLactoseFree ? "Lactose Free Bois" : "Shit bruh"}

                <br/>
                <label>
                    <input
                        type="checkbox"
                        name="testing.Sammy"
                        onChange={this.handleChangeSelect}
                        checked={this.state.testing.Sammy}
                    /> Sammy?
                    <br/>
                    {this.state.testing.Sammy ? "Lactose Free Bois" : "Shit bruh"}
                </label>
            </div>

        )
    }
}

    export default survey