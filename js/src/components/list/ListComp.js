import React from 'react'
import {compose, multiply, prop, range} from 'ramda'
import './list.sass'
import {List} from 'src/algo/list'
import listRenderer from 'src/components/list/listRenderer'
import {sorts} from 'src/algo/sorting'
import {withStateHandlers} from 'recompose'
import {player} from 'src/algo/player'
import {shuffle} from 'src/algo/shuffle'

const data = shuffle(List(
    range(0, 30).map(multiply(3))
))

const h = 500
const w = 600

const ListComp = ({sorting, onSort, _ref}) =>
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



class Cont extends React.Component {

    ref = el => this.el = el
    renderList

    componentDidMount() {
        this.renderList = listRenderer({el: this.el})
        this.renderList(data)
    }

    onSort = sort => {
        const {steps} = sort(data.clone())
        const play = player({
            steps,
            playStep: this.renderList,
            tempo: 20,
            onDone: () => this.setState({sorting: false})
        })

        this.setState({sorting: true})
        play()
    }

    render = () =>
        <ListComp {...this.props}
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
