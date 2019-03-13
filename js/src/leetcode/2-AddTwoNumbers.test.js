function ListNode(val) {
    this.val = val
    this.next = null
}

ListNode.prototype.toString = function() {
    return '(' + this.val + ',' + (this.next ? this.next : 'null') + ')'
}

ListNode.prototype.equals = function(other) {
    if (other === null) return false

    if (this.val !== other.val) return false

    if (this.next == null) return other.next == null
    else return this.next.equals(other.next)
}

const list = (...vals) => {
    const [first, ...rest] = vals
    const head = new ListNode(first)

    let current = head
    for (let v of rest) {
        const next = new ListNode(v)
        current.next = next
        current = next
    }
    return head
}

const addTwoNumbers = function(l1, l2) {
    let carry = 0
    let head
    let current
    while (l1 || l2) {
        let sum = (l1 ? l1.val : 0) + (l2 ? l2.val : 0) + carry

        if (sum > 9) {
            sum = sum - 10
            carry = 1
        } else {
            carry = 0
        }
        if (!head) {
            head = new ListNode(sum)
            current = head
        } else {
            const next = new ListNode(sum)
            current.next = next
            current = next
        }
        l1 = l1 && l1.next
        l2 = l2 && l2.next
    }

    if (carry) {
        current.next = new ListNode(carry)
    }

    return head
}

describe('Two sum', () => {
    it('simple', () => {
        expect(
            addTwoNumbers(list(2, 4, 3), list(5, 6, 4)).equals(list(7, 0, 8))
        ).toBeTruthy()
    })
})
