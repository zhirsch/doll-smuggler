(in-ns `doll-smuggler.core)

(load "types")

;; This function implements a state machine for parsing an input file, which
;; looks something like:
;;
;;   max weight: 400
;;
;;   available dolls:
;;
;;   name    weight value
;;   luke        9   150
;;   anthony    13    35
;;   candice   153   200
;;
;; The states that are progressed through are:
;;   1. Find "max weight"
;;   2. Find "available dolls"
;;   3. Find the dolls header
;;   4. Find each doll
;;
;; The states are progressed through strictly linearly, i.e. those states
;; must be encountered in order and cannot be skipped or back-tracked.

(defn consume-doll
  [problem line]
  (let [[match name weight value] (re-matches #"(\S+)\s+(\d+)\s+(\d+)" line)]
    (if
     match
      ;; Found a doll, add it to the Problem and next look for more dolls.
      (let [weight (Float/parseFloat weight) value (Float/parseFloat value)]
        [(add-doll problem (make-doll name weight value)) consume-doll])
      ;; No doll found, keep looking.
      [problem consume-doll])))

(defn consume-dolls-header
  [problem line]
  (if
   (re-matches #"name\s+weight\s+value" line)
    ;; Found the dolls header, next look for the doll definitions.
    [problem consume-doll]
    ;; Not found, keep looking.
    [problem consume-dolls-header]))

(defn consume-available-dolls
  [problem line]
  (if
   (re-matches #"available dolls:" line)
    ;; Found "available dolls" line, next look for the dolls header.
    [problem consume-dolls-header]
    ;; Next found, keep looking.
    [problem consume-available-dolls]))

(defn consume-max-weight
  [problem line]
  (let [[match max-weight] (re-matches #"max weight: (\d+)" line)]
    (if
     match
      ;; Found the "max weight" line, create the Problem and next look for
      ;; the "available dolls" line.
      [(make-problem (Float/parseFloat max-weight)) consume-available-dolls]
      ;; Not found, keep looking.
      [problem consume-max-weight])))

(defn parse-file
  "Reads the contents of a file and parses them into a Problem."
  [file]
  (let [lines (clojure.string/split-lines file)]
    ;; Loop over each line, keeping track of the Problem that has been built
    ;; so far and next function to call in the state machine.
    (loop [lines lines, problem nil, nextfn consume-max-weight]
      (if lines
        (if (empty? (first lines))
          ;; Skip empty lines.
          (recur (next lines) problem nextfn)
          ;; Call the state machine function, and move to the next iteration
          ;; of the loop.  The nextfn returns the (potentially) modified
          ;; Problem and the next state machine function to call.
          (let [state (nextfn problem (first lines))]
            (recur (next lines) (get state 0) (get state 1))))
        ;; If there are no more lines, return the Problem.
        problem))))
