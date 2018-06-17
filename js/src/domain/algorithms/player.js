import { from, interval, zip } from 'rxjs'

export const play = ({steps, tempo = 500}) =>
    zip(
        from(steps),
        interval(tempo),
        x => x)
