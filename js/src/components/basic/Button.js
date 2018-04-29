import React from 'react'
import s from './Button.sass'

const Button = ({label, enabled = true, ...rest}) =>
    <button className={s.button}
            disabled={!enabled}
            {...rest}>{label}</button>

export default Button


