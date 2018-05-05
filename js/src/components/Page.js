import React, {Component} from 'react'
import s from './page.sass'

import {Route} from 'react-router'
import View from 'src/components/basic/View'
import {Header} from 'semantic-ui-react'

const Page = ({path, title, component: Component}) =>
    <Route path={path}
           key={path}
           render={({match}) =>
               <View className={s.root}>
                   <Header as="h1">{title}</Header>
                   <Component {...match.params}/>
               </View>
           }/>


export default Page

