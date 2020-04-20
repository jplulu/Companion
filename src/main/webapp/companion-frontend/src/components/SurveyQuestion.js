import React, {Component} from "react";


function SurveyQuestion(props) {
    const numOptions = props.options.length

    let OptionsIterated = props.options.map(option => {
        return(
            <option value="male">{option}</option>

            )
        }
    )


   return(
        <div>
            <p>{props.question}</p>
            <select>
                <option>{props.default}</option>
                {OptionsIterated}
            </select>
        </div>
    )
}




export default SurveyQuestion