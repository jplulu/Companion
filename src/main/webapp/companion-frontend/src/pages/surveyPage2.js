import React, { Component } from 'react'
import  CheckBox  from '../components/CheckBox'
import SurveySports from '../components/survey/SurveySports'
import SurveyMusic from '../components/survey/SurveyMusic'


class surveyPage2 extends Component {
    constructor(props) {
        super(props)
        this.state = {

        }

    }

    render() {
        return (
            <div>
               <SurveySports />
               <SurveyMusic />
            </div>
        );
    }
}

export default surveyPage2