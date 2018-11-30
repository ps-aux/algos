const radix = 10

export const countDigits = n => {
    let l = 1
    if (n >= 100000000) {
        l += 8
        n /= 100000000
    }
    if (n >= 10000) {
        l += 4
        n /= 10000
    }
    if (n >= 100) {
        l += 2
        n /= 100
    }
    if (n >= 10) {
        l += 1
    }
    return l
}

export const sortByDigitPosition = (input, pos) => {

    const byDigit = []

    // Yer old count sort
    input.forEach(n => {
        const digit = getDigitAtPosition(n, pos)
        const bucket = byDigit[digit] || (byDigit[digit] = [])
        bucket.push(n)
    })

    const out = []

    byDigit.forEach(b => {
        if (!b)
            return
        b.forEach(n => out.push(n))
    })

    return out
}

export const getDigitAtPosition = (n, pos) => {
    const divider = Math.pow(radix, pos)

    const divided = Math.floor(n / divider)

    return divided % radix

}

export const radixSort = input => {
    const digiCount = countDigits(input[0])

    let arr = input
    for (let i = 0; i < digiCount; i++) {
        arr = sortByDigitPosition(arr, i)
    }

    return arr
}

