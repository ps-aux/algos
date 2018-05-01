import React from 'react'
import s from './Button.sass'
import {Button as ButtonImpl} from 'semantic-ui-react'

const Button = ({label, enabled = true, ...rest}) =>
    <ButtonImpl disabled={!enabled}
                {...rest} >{label}</ButtonImpl>
// <button className={s.button}
//         disabled={!enabled}
//         {...rest}>{label}</button>

export default Button


