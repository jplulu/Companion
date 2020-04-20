import React, {Component} from "react";
import SurveyQuestion from "../components/SurveyQuestion";

class survey extends Component{
    constructor(props){
        super(props);
        this.state = {
                loading: false,
                question1: "Quack",
                genderPreference: "None"
        }
        this.handleChange = this.handleChange.bind(this)
    }

    handleChange(event){
        const {name, value, type} = event.target
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
                    onChange = {this.handleChange}
                >
                    <option value="">-- Please Choose a preference --</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="none">No Preference</option>

                </select>

                <SurveyQuestion
                    default = "-- Please Select an Option --"
                    question = "What is your gender preference?"
                    options = {["male","female","no preference"]}
                />

                <SurveyQuestion
                    default = "-- Please Select an Option --"
                    question = "What age range are you interested in meeting?"
                    options = {["Basketball","Swimming","Football","Knitting","Dancing"]}
                />

            </div>
        )
    }


}

export default survey