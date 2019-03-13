import React from 'react'
import Collection from 'src/components/basic/Collection'
import { prop } from 'ramda'

const Bar = ({ x, y, height, width }) => (
    <rect x={x} y={y} width={width} height={height} />
)

const layout = ({ width, height, margin = width / 3 }) => (value, i) => ({
    value,
    x: i * (width + margin),
    y: height - value,
    height: value,
    width
})

const List = ({
    items,
    width,
    height,
    itemWidth = 10,
    itemMargin = itemWidth / 3
}) => (
    <svg width={width} height={height}>
        <Collection
            items={items.map(
                layout({
                    width: itemWidth,
                    height,
                    margin: itemMargin
                })
            )}
            id={prop('value')}
            comp={Bar}
        />
    </svg>
)

export default List
