entity unit is
    port (
        a, b, c : in bit;
        x, y : out bit);
end unit;

architecture rtl of unit is
begin
    -- U1 = A or B
    -- U2 = B nor C
    -- U3 = A xor C
    -- U4 = (not U1) nor (not U2) = U1 and U2
    -- U5 = (not U2) and (not U3) = U2 nor U3
    x <= (a or b) and (b nor c);
    y <= (b nor c) nor (a xor c);
end rtl;
