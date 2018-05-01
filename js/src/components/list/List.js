import React from 'react'
import s from './List.sass'
import listRenderer from './listRenderer'

const h = 400
const w = 1400

const List = ({rendererRef}) =>
    <div className={s.container}>
        <svg ref={el => {
            rendererRef(listRenderer(el))
        }}
             height={h} width={w}/>
    </div>


export default List
