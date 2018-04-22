import React from 'react'
import PropTypes from 'prop-types'

const factor = 10
const _Array = ({array}) =>
    <div className="array">
        {array.map((h, i) => <Column key={i}
                                     height={h}/>)}
    </div>

const Column = ({height}) =>
    <div className="column" style={{height: `${height * factor}px`}}>
        <span>{height}</span>
    </div>

_Array.propTypes = {
    array: PropTypes.array.isRequired
}

export default _Array