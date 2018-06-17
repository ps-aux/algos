import React from 'react'

const Collection = ({
                        items, comp: Comp,
                        id = i => i.id,
                        props
                    }) =>
    items.map(i => ({...i, ...props}))
        .map(i =>
            <Comp {...i} key={id(i)}
                  onClick={i.onClick && (() => i.onClick(i))}/>)

export default Collection