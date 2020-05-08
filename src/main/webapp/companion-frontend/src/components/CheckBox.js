import React from 'react'

export const CheckBox = props => {
    return (
        <div>
            <input
                key={props.id}
                onClick={props.handleCheckChildElement}
                type="checkbox" checked={props.isChecked}
                value={props.value} />
                {props.name}
        </div>
    )
};

export default CheckBox