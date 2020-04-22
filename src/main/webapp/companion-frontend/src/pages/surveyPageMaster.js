import React, { Component } from 'react'
import  CheckBox  from '../components/CheckBox'

class surveyPageMaster extends Component {
    constructor(props) {
        super(props)
        this.state = {
            questions: ["Which of the following sports do you enjoy playing?",
                "Which genres of music do you enjoy listening too?",
                "What types of food do you love?",
                "What hobbies are you interested in?",
                "How would you describe your personality type?",
                "Do you consider yourself an animal lover?"],
            sports: [
                {id: 1, value: "basketball", name:"Basketball", isChecked: false},
                {id: 2, value: "football", name:"Football", isChecked: false},
                {id: 3, value: "baseball", name:"Baseball", isChecked: false},
                {id: 4, value: "volleyball", name:"Volleyball", isChecked: false}
            ],
            music: [
                {id: 1, value: "rock", name:"Rock", isChecked: false},
                {id: 2, value: "pop", name:"Pop", isChecked: false},
                {id: 3, value: "country", name:"Country", isChecked: false},
                {id: 4, value: "r&b/soul", name:"R&B/Soul", isChecked: false},
                {id: 5, value: "hiphop", name:"HipHop", isChecked: false},
                {id: 6, value: "easylistening", name:"Easy Listening", isChecked: false},
                {id: 7, value: "electronic/dance", name:"Electronic/Dance", isChecked: false}
            ],
            food: [
                {id: 1, value: "pizza", name:"Pizza", isChecked: false},
                {id: 2, value: "halal", name:"Halal", isChecked: false},
                {id: 3, value: "kosher", name:"Kosher", isChecked: false},
                {id: 4, value: "chinese", name:"Chinese", isChecked: false},
                {id: 5, value: "caribbean", name:"Caribbean", isChecked: false},
                {id: 6, value: "mexican", name:"Mexican", isChecked: false},
                {id: 7, value: "italian", name:"Italian", isChecked: false},
                {id: 8, value: "sushi", name:"Sushi", isChecked: false},
                {id: 9, value: "indian", name:"Indian", isChecked: false},
                {id: 10, value: "fastfood", name:"Fast Food", isChecked: false}
            ],
            hobby: [
                {id: 1, value: "video games", name:"Video Games", isChecked: false},
                {id: 2, value: "programming", name:"Programming", isChecked: false},
                {id: 3, value: "cooking", name:"Cooking", isChecked: false},
                {id: 4, value: "exercising", name:"Exercising", isChecked: false},
                {id: 5, value: "singing", name:"Singing", isChecked: false},
                {id: 6, value: "dancing", name:"Dancing", isChecked: false},
                {id: 7, value: "clubbing", name:"Clubbing", isChecked: false},
                {id: 8, value: "coin collecting", name:"Coin Collecting", isChecked: false},
                {id: 9, value: "shopping", name:"Shopping", isChecked: false},
                {id: 10, value: "quarantining", name:"Quarantining", isChecked: false}
            ],

            sportsAnswers: [8,4,7,5],
            musicAnswers: [5,4,3,2,1,0],
            foodAnswers: [5,4,3,2,1,0,1,2,3,4,5],
            hobbyAnswers: [6,3,7,3],
            personalityAnswer: 0,
            animalAnswer: 0,
            genderPreference: null,
            maxAge: null,
            minAge: null,
            maxDistance: null,
            surveyAnswers: {sport: [], food: [], music: [], hobby: [], personalityType: null, likesAnimals: null, genderPreference: null, maxAge: null, minAge: null, maxDistance: null}
        }


        this.handleCheckSports = this.handleCheckSports.bind(this)
        this.handleCheckMusic = this.handleCheckMusic.bind(this)
        this.handleCheckFood = this.handleCheckFood.bind(this)
        this.handleCheckHobby = this.handleCheckHobby.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    /* Write this function to fetch the user and the specific user music answers */
    componentDidMount() {

    }

    componentDidUpdate() {
        if (this.props.onChange) {
            this.props.onChange(this.state);
        }
    }

    handleCheckSports = (event) => {
        let options = this.state.sports
        options.forEach(option => {
            if (option.value === event.target.value)
                option.isChecked =  event.target.checked
        })
        this.setState({sports: options})

        this.setState({sportsAnswers: null});
        let sportTent = this.state.sports.map(option => {
                if(option.isChecked)
                    return(
                        option.id
                    )
            }
        )
        let sportExpress = sportTent.filter(el => {
            return el !== undefined;
        })
        this.setState({sportsAnswers: sportExpress})    }

    handleCheckMusic = (event) => {
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
            return el !== undefined;
        })
        this.setState({musicAnswers: musicExpress})    }

    handleCheckFood = (event) => {
        let options = this.state.food
        options.forEach(option => {
            if (option.value === event.target.value)
                option.isChecked =  event.target.checked
        })
        this.setState({food: options})

        this.setState({foodAnswers: null});
        let foodTent = this.state.food.map(option => {
                if(option.isChecked)
                    return(
                        option.id
                    )
            }
        )
        let foodExpress = foodTent.filter(el => {
            return el !== undefined;
        })
        this.setState({foodAnswers: foodExpress})    }

    handleCheckHobby = (event) => {
        let options = this.state.hobby
        options.forEach(option => {
            if (option.value === event.target.value)
                option.isChecked =  event.target.checked
        })
        this.setState({hobby: options})

        this.setState({hobbyAnswers: null});
        let hobbyTent = this.state.hobby.map(option => {
                if(option.isChecked)
                    return(
                        option.id
                    )
            }
        )
        let hobbyExpress = hobbyTent.filter(el => {
            return el !== undefined;
        })
        this.setState({hobbyAnswers: hobbyExpress})
    }

    handleChange(event) {
        const {name, value} = event.target
        this.setState({
            [name]: value
        })
    }

    onChange(e) {
        const { surveyAnswers } = {...this.state };
        const currentState = surveyAnswers;
        const {name, value} = e.target;
        currentState[name] = value;

        this.setState({ surveyAnswers: currentState })
    }

    handleSubmit(event) {
        this.setState()
        event.preventDefault();
    }

    render() {
        return (
            <div className="App">
                <form onSubmit={this.handleSubmit}>
                <div>
                    <h2> What type of friends are you looking for></h2>
                    <label>
                        <input
                            type="radio"
                            name="genderPreference"
                            value="male"
                            checked={this.state.animalAnswer === "male"}
                            onChange={this.handleChange}
                        /> Male
                    </label>

                    <br />

                    <label>
                        <input
                            type="radio"
                            name="genderPreference"
                            value="female"
                            checked={this.state.genderPreference === "female"}
                            onChange={this.handleChange}
                        /> Female
                    </label>
                    <br />

                    <label>
                        <input
                            type="radio"
                            name="genderPreference"
                            value="no preference"
                            checked={this.state.genderPreference === "no preference"}
                            onChange={this.handleChange}
                        /> No preference

                    </label>
                    <p>You are looking for: {this.state.genderPreference}</p>
                </div>

                <div>
                    <h2>What is the age range you are looking for in new friends?</h2>
                    <input
                        name="minAge"
                        value={this.state.minAge}
                        onChange={this.handleChange}
                        placeholder="Minimum Age"
                    />
                    -

                    <input
                        name="maxAge"
                        value={this.state.maxAge}
                        onChange={this.handleChange}
                        placeholder="Maximum Age"
                    />
                    <br />

                    <h2>What is the maximum distance you would like potential friends to be from you (miles)?</h2>
                    <input
                        name="maxDistance"
                        value={this.state.maxDistance}
                        onChange={this.handleChange}
                        placeholder="Maximum Distance"
                    />
                </div>

                <div>
                    <h2> {this.state.questions[0]}</h2>
                    <ul>
                        {this.state.sports.map(selection => {
                            return (<CheckBox handleCheckChildElement={this.handleCheckSports}  {...selection} />)})
                        }
                    </ul>
                    <br/>You like these types of sports: {this.state.sportsAnswers}
                    <br/>{this.state.sportsAnswers.length}
                    <hr/>
                </div>

                <div>
                    <h2> {this.state.questions[1]}</h2>
                    <ul>
                        {this.state.music.map(selection => {
                                return (<CheckBox handleCheckChildElement={this.handleCheckMusic}  {...selection} />)})
                        }
                    </ul>
                    <br/>You like these types of music: {this.state.musicAnswers}
                    <br/>{this.state.musicAnswers.length}
                    <hr/>
                </div>

                <div>
                    <h2> {this.state.questions[2]}</h2>
                    <ul>
                        {this.state.food.map(selection => {
                            return (<CheckBox handleCheckChildElement={this.handleCheckFood}  {...selection} />)})
                        }
                    </ul>
                    <br/>You like these types of food: {this.state.foodAnswers}
                    <br/>{this.state.foodAnswers.length}
                    <hr/>
                </div>

                <div>
                    <h2> {this.state.questions[3]}</h2>
                    <ul>
                        {this.state.hobby.map(selection => {
                            return (<CheckBox handleCheckChildElement={this.handleCheckHobby}  {...selection} />)})
                        }
                    </ul>
                    <br/>I enjoy these things: {this.state.hobbyAnswers}
                    <br/>{this.state.hobbyAnswers.length}
                    <hr/>
                </div>

                <div>
                    <h2> {this.state.questions[4]}</h2>
                    <label>
                        <input
                            type="radio"
                            name="personalityAnswer"
                            value="extroverted"
                            checked={this.state.personalityAnswer === "extroverted"}
                            onChange={this.handleChange}
                        /> Extroverted
                    </label>

                    <br />

                    <label>
                        <input
                            type="radio"
                            name="personalityAnswer"
                            value="introverted"
                            checked={this.state.personalityAnswer === "introverted"}
                            onChange={this.handleChange}
                        /> Introverted
                    </label>
                    <p>You are: {this.state.personalityAnswer}</p>
                    <hr/>
                </div>

                <div>
                    <h2> {this.state.questions[5]}</h2>
                    <label>
                        <input
                            type="radio"
                            name="animalAnswer"
                            value="yes"
                            checked={this.state.animalAnswer === "yes"}
                            onChange={this.handleChange}
                        /> yes
                    </label>

                    <br />

                    <label>
                        <input
                            type="radio"
                            name="animalAnswer"
                            value="no"
                            checked={this.state.animalAnswer === "no"}
                            onChange={this.handleChange}
                        /> no
                    </label>
                    <p>You are: {this.state.animalAnswer}</p>
                    <hr/>
                </div>

                <input type="submit" value="Submit" />
                <br/>
                    <br/>
                    <br/>
                    <br/>
                </form>
            </div>
        );
    }
}

export default surveyPageMaster