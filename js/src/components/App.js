import React from 'react'

import Tree from 'src/components/tree/Tree'
import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'
import Sorting from 'src/components/sorting/Sorting'
import {Route} from 'react-router'
import View from 'src/components/basic/View'
import HorizView from 'src/components/basic/HorizView'
import {Link} from 'react-router-dom'


const h = heap()

range(1, 10).forEach(v => h.insert(v))


const App = () =>
    <View className="app">
        <HorizView>
            <Link to="/sorting">sorting</Link>
            <Link to="/tree">tree</Link>
        </HorizView>
        <Route path="/sorting" component={Sorting}/>
        <Route path="/tree" component={Tree}/>
    </View>

export default App