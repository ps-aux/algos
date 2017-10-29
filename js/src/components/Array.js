import React from 'react'
import PropTypes from 'prop-types'

const factor = 10
const Array = ({array}) =>
    <div className="array">
        {array.map(v => v * factor)
            .map((h, i) => <Column key={i}
                                   height={h}/>)}
    </div>

const Column = ({height}) =>
    <div className="column" style={{height: `${height}px`}}/>

Array.propTypes = {
    array: PropTypes.array.isRequired
}

export default Array