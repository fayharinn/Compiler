let x =
  let y =
   let v1 = 1 in
   let v2 = 2 in
   v1 + v2
   in y

in let rec succ x =
   let v3 = x in
   let v4 = 1 in
   v3 + v4

in let rec double x =
   let v5 = 2 in
   let v6 = x in
   v5 - v6

in let v7 =
   let v8 =
     let v9 = x in double v9 in
   succ v8
in print_int v7
