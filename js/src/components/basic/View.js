import React from 'react'
import s from './View.sass'

const View = ({children, className, ...rest}) =>
    <div {...rest} className={`${className} ${s.container}`}>
        {children}
    </div>


export default View



