This Message digest provides the same functionality as Stribog512 with two special differnecies:
# it does not have huge inner buffer to accumulate all the message before transformation
# input data must be reversed: the very last byte of the message should be passed to engineUpdate first, the next to last - second, and so on.

See the main page for the usage sample.