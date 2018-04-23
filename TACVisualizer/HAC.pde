//  Simple Language
//  program ::= statementlist $
//  statementlist ::= statement statementlist | ^
//  statement ::= INT ID inplaceAssign expression ;
//              | expression ;
//              | FOR ( INT ID = expression ; condExpr ; expression ) body
//              | WHILE ( condExpr ) body
//              | IF ( condExpr ) body conditionalControl
//  conditionalControl ::= ^
//                       | ELSE body
//                       | ELIF ( condExpr ) body conditionalControl
//  body ::= { statementlist }
//  inplaceAssign ::= += | *= | -= | = 
//  baseValue ::= ID | NUMBER | ( expression ) | - baseValue
//  factor ::= baseValue factorEnd
//  factorEnd ::= * baseValue factorEnd
//              | / baseValue factorEnd
//              | ^
//  term ::= factor termEnd
//  termEnd ::= + factor termEnd
//            | - factor termEnd
//            | << factor termEnd
//            | >> factor termEnd
//            | ^
//  condExpr ::= term condExprEnd
//  condExprEnd ::= && term condExprEnd
//                | || term condExprEnd
//                | < term condExprEnd
//                | > term condExprEnd
//                | >= term condExprEnd
//                | <= term condExprEnd
//                | ^
//   expression ::= condExpr
// 