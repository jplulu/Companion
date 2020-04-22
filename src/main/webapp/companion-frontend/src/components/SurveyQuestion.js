import React, {Component} from "react";


function SurveyQuestion(props) {
    const numOptions = props.options.length

    {
        if (props.type === 'select') {
            let OptionsIterated = props.options.map(option => {
                    return (
                        <option value="male">{option}</option>
                    )
                }
            )

            return (
                <div>
                    <p>{props.question}</p>
                    <select>
                        <option>{props.default}</option>
                        {OptionsIterated}
                    </select>
                </div>
            )
        }

        else if (props.type === 'check') {

            /* Looping through all the options for a question, assigning the name value*/
            let names_checks = props.options.map((option, index) => {
                return(
                    `this.state.answers[0]${index}`
                )
            })

            let OptionsIterated = props.options.map((option,index) => {
               return (
                        <label>
                            <input
                                type="checkbox"
                                name = {names_checks[index]}
                                checked={props.answers[index]}


                            /> {option}
                            <br/>
                        </label>

                    )
                }
            )

            return (
                <div>
                <h2>{props.question}</h2>

                {OptionsIterated}
                </div>
            )
        }
        return (
            <p>{props.question}</p>
        )

    }
}


export default SurveyQuestion