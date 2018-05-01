import React from 'react'

import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'
import Sorting from 'src/components/sorting/Sorting'
import {Route} from 'react-router'
import View from 'src/components/basic/View'
import {Link} from 'react-router-dom'
import Bst from 'src/components/tree-algos/Bst'
import Graph from 'src/components/graph/Graph'


const h = heap()

range(1, 10).forEach(v => h.insert(v))


const App = () =>
    <View className="app">
        <View>
            <Link to="/sorting">Sorting</Link>
            <Link to="/bst">Bst</Link>
            <Link to="/graph">Graph</Link>
        </View>
        <Route path="/sorting" component={Sorting}/>
        <Route path="/bst" component={Bst}/>
        <Route path="/graph" component={Graph}/>
    </View>

export default App