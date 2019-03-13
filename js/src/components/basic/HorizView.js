import React from 'react'
import s from './HorizView.sass'

const HorizView = ({ children, className, ...rest }) => (
    <div {...rest} className={`${className} ${s.container}`}>
        {children}
    </div>
)

export default HorizView
