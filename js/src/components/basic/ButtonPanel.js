import React from 'react'
import { Button as SemButton } from 'semantic-ui-react'
import Button from 'src/components/basic/Button'

const ButtonPanel = ({ actions, onClick, enabled }) => (
    <SemButton.Group>
        {actions.map((a, i) => (
            <Button
                key={i}
                label={a.label}
                enabled={enabled}
                onClick={() => onClick(a)}
            />
        ))}
    </SemButton.Group>
)

export default ButtonPanel
