import React from 'react'
import {compose, multiply, prop, range} from 'ramda'
import './list.sass'
import {List} from 'src/algo/list'
import listRenderer from 'src/components/list/listRenderer'
import {sorts} from 'src/algo/sorting'
import {withStateHandlers} from 'recompose'
import {player} from 'src/algo/player'
import {shuffle} from 'src/algo/shuffle'
import Button from 'src/components/basic/Button'
import s from './ButtonPanel.sass'
import lVs from './ListView.sass'

const data = shuffle(List(
    range(0, 100).map(multiply(3))
))

const h = 400
const w = 1400

const ButtonPanel = ({sorts, onClick, enabled = true}) =>
    <div className={s.buttonPanel}>
        {Object.entries(sorts)
            .map(([k, v]) =>
                <Button key={k} label={k}
                        enabled={enabled}
                        onClick={() => onClick(v)}/>)}
    </div>


const ListView = ({_ref}) =>
    <div className={lVs.container}>
        <svg ref={_ref}
             height={h} width={w}/>
    </div>

class Cont extends React.Component {

    ref = el => this.el = el
    renderList
    state = {sorting: false}

    componentDidMount() {
        this.renderList = listRenderer({el: this.el})
        this.renderList(data)
    }

    onSort = sort => {
        const {steps} = sort(data.clone())
        const play = player({
            steps,
            playStep: this.renderList,
            tempo: 5,
            onDone: () => this.setState({sorting: false})
        })

        this.setState({sorting: true})
        play()
    }

    render = () => {
        const {sorting} = this.state
        return <div>
            <ButtonPanel sorts={sorts}
                         enabled={!sorting}
                         onClick={this.onSort}/>
            <ListView _ref={this.ref}/>
        </div>
    }

}

export default Cont

