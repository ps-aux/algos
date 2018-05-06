import React from 'react'
import {Button as ButtonImpl} from 'semantic-ui-react'

const Button = ({label, enabled = true, ...rest}) =>
    <ButtonImpl disabled={!enabled}
                {...rest} >{label}</ButtonImpl>

export default Button


