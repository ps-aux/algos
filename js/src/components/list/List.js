import React from 'react'
import s from './List.sass'
import Collection from 'src/components/basic/Collection'
import {identity, prop} from 'ramda'

const h = 400
const w = 1400

const width = 10
const margin = width / 2

const Bar = ({x, y, height, width}) =>
    <rect x={x} y={y}
          width={width}
          height={height}/>

const layout = ({width, margin, height}) => (val, idx) => ({
    value: val,
    x: idx * (width + margin),
    y: height - val,
    height: val,
    width
})

const List = ({items}) =>
    <div className={s.container}>
        <svg height={h} width={w}>
            <Collection items={items.map(layout({width, margin, height: h}))}
                        id={prop('value')}
                        comp={Bar}/>
        </svg>
    </div>


export default List
