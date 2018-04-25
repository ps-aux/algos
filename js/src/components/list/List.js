import React from 'react'
import {compose, multiply, prop, range} from 'ramda'
import './list.sass'
import {list} from 'src/algo/list'
import listRenderer from 'src/components/list/listRenderer'
import {sorts} from 'src/algo/sorting'
import {withStateHandlers} from 'recompose'

const data = list([
    8, 5, 2, 9, 4, 8, 1, 20, 67, 67, 17
].map(multiply(10)))

const h = 500
const w = 600

const mount = el => {
}


const List = ({sorting, onSort, _ref}) =>
    <div className="list">
        {!sorting &&
        <div>
            {Object.entries(sorts)
                .map(([k, v]) =>
                    <button key={k}
                            onClick={() => onSort(v)}>{k}</button>)}
        </div>}
        This is list
        <svg ref={_ref}
             height={h} width={w}/>
    </div>

const player = ({steps, playStep, tempo = 500, onDone}) => () => {
    const play = () => {
        const s = steps.shift()
        if (!s)
            return onDone()

        playStep(s)
        setTimeout(play, tempo)
    }
    play()
}

class Cont extends React.Component {

    ref = el => this.el = el
    renderList

    componentDidMount() {
        this.renderList = listRenderer({el: this.el})
        this.renderList(data)
    }

    onSort = sort => {
        const {steps} = sort(data.clone())
        const start = player({
            steps,
            playStep: this.renderList,
            tempo: 200,
            onDone: () => this.setState({sorting: false})
        })

        this.setState({sorting: true})
        start()
    }

    render = () =>
        <List {...this.props}
              {...this.state}
              onSort={this.onSort}
              _ref={this.ref}/>

}

export default compose(
    withStateHandlers(
        {sorting: false},
        {
            sort: () => () => ({sorting: true}),
            go: (_, {el}) => () => {
                console.log('go')
                el.addEventListener('click', () => console.log('ieeeeeeeee'))
            }
        }))
(Cont)
