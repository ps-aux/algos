export const flatMap = (f, l) => l.reduce((a, i) =>
    (Array.prototype.push.apply(a, f(i)), a), [])
