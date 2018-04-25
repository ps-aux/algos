import React from 'react'
import * as d3 from 'd3'
import './list.sass'

const draw = (el, list,
              {
                  height = 100, width = 30,
                  margin = 10
              } = {}) => {
    const svg = d3.select(el)


    const bars = svg.selectAll('rect')
        .data(list)

    bars.enter()
        .append('rect')
        .merge(bars)
        .attrs((d, i) => ({
            x: i * (width + margin),
            y: height - d,
            height: d,
            width
        }))

    bars.exit().remove()

}

const create = ({el}) => {
    return data => draw(el, data)
}


export default create
