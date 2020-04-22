import React, { Component } from 'react'
import  CheckBox  from '../CheckBox'

class SurveySports extends Component {
    constructor(props) {
        super(props)
        this.state = {
            questions: ["Which sports do you enjoy playing?", "food", "music", "hobby", "personalityType", "likesAnimals"],
            sports: [
                {id: 1, value: "basketball", name:"Basketball", isChecked: false},
                {id: 2, value: "football", name:"Football", isChecked: false},
                {id: 3, value: "baseball", name:"Baseball", isChecked: false},
                {id: 4, value: "volleyball", name:"Volleyball", isChecked: false}
            ],
            sportsAnswers: [8,4,7,5]
        }
        this.handleCheck = this.handleCheck.bind(this)
    }

    handleCheck = (event) => {
        let options = this.state.sports
        options.forEach(option => {
            if (option.value === event.target.value)
                option.isChecked =  event.target.checked
        })
        this.setState({sports: options})

        this.setState({sportsAnswers: null});
        let sportsTent = this.state.sports.map(option => {
            if(option.isChecked)
            return(
                option.id
                 )
             }
        )
        this.setState({sportsAnswers: sportsTent})    }

    render() {
        return (
            <div className="App">
                <h2> {this.state.questions[0]}</h2>

                <ul>
                    {
                        this.state.sports.map(selection => {
                            return (<CheckBox handleCheckChildElement={this.handleCheck}  {...selection} />)
                        })
                    }
                </ul>
                {this.state.sports[0].isChecked ? "Yes Banana":"No Banana"}
                <br/>{this.state.sportsAnswers[0]}
                <br/>{this.state.sportsAnswers[1]}
                <br/>{this.state.sportsAnswers[2]}
                <br/>{this.state.sportsAnswers[3]}

            </div>
        );
    }
}

export default SurveySports