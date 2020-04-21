import React, { Component } from 'react'
import  CheckBox  from '../CheckBox'

class SurveyMusic extends Component {
    constructor(props) {
        super(props)
        this.state = {
            questions: ["What genres of music do you enjoy listening too?", "food", "music", "hobby", "personalityType", "likesAnimals"],
            music: [
                {id: 1, value: "rock", name:"Rock", isChecked: false},
                {id: 2, value: "pop", name:"Pop", isChecked: false},
                {id: 3, value: "country", name:"Country", isChecked: false},
                {id: 4, value: "r&b/soul", name:"R&B/Soul", isChecked: false},
                {id: 5, value: "hiphop", name:"HipHop", isChecked: false},
                {id: 6, value: "easylistening", name:"Easy Listening", isChecked: false},
                {id: 7, value: "electronic/dance", name:"Electronic/Dance", isChecked: false}
            ],
            musicAnswers: [5,4,3,2,1,0,1,2,3,4,5]
        }
        this.handleCheck = this.handleCheck.bind(this)
    }

    handleCheck = (event) => {
        let options = this.state.music
        options.forEach(option => {
            if (option.value === event.target.value)
                option.isChecked =  event.target.checked
        })
        this.setState({music: options})

        this.setState({musicAnswers: null});
        let musicTent = this.state.music.map(option => {
                if(option.isChecked)
                    return(
                        option.id
                    )
            }
        )
        let musicExpress = musicTent.filter(el => {
            return el != 0;
        })
        this.setState({musicAnswers: musicExpress})    }

    render() {
        return (
            <div className="App">
                <h2> {this.state.questions[0]}</h2>

                <ul>
                    {
                        this.state.music.map(selection => {
                            return (<CheckBox handleCheckChildElement={this.handleCheck}  {...selection} />)
                        })
                    }
                </ul>

                <br/>{this.state.musicAnswers}
                <br/>{this.state.musicAnswers.length}


            </div>
        );
    }
}

export default SurveyMusic