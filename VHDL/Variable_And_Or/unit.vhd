library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity unit is
  port (
    a, b, c : in std_logic;
    s0 : in std_logic;
    x : out std_logic
  );
end unit;

architecture Synthetic of unit is

  component gateOR3
    port (
      X, Y, Z : in std_logic;
      O : out std_logic);
  end component;

  component gateAND3
    port (
      X, Y, Z : in std_logic;
      O : out std_logic);
  end component;

  component or_and
    port (
      a, b, s0 : in std_logic;
      x : out std_logic
    );
  end component;

  signal s1, s2 : std_logic;

begin

  G1 : gateOR3 port map(a, b, c, s1);
  G2 : gateAND3 port map(a, b, c, s2);
  G3 : or_and port map(s1, s2, s0, x);

end Synthetic;
