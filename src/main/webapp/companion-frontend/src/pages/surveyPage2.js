import React, { Component } from 'react'
import  CheckBox  from '../components/CheckBox'
import SurveySports from '../components/survey/SurveySports'
import SurveyMusic from '../components/survey/SurveyMusic'


function surveyPage2(){
    let musicData = {}
    const eventhandler = data => musicData = data

        return (
            <div>
               <SurveySports />
               <SurveyMusic onChange={eventhandler} />
               <hr/>
               <h2>EVERYTHING PAST THIS LINE IS MAIN SURVEY PAGE</h2>
                {musicData.questions}
            </div>
        );
    }


export default surveyPage2