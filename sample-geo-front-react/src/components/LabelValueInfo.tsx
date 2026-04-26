import { Grid, Link as MuiLink, Stack, Typography } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

type Props = {
  label: string;
  value: string | null;
  href?: string;
  gridSize?: number;
};

export default function ClearableTextField({
  label,
  value,
  href,
  gridSize = 6,
}: Props) {
  const isExternal = href ? /^(https?:)?\/\//.test(href) : false;

  return (
    <Grid size={gridSize} sx={{ mt: 4 }}>
      <Stack direction="column">
        {href ? (
          isExternal ? (
            <MuiLink href={href} target="_blank" rel="noopener noreferrer">
              <Typography variant="body1" color="primary">
                {value || "-"}
              </Typography>
            </MuiLink>
          ) : (
            <MuiLink component={RouterLink} to={href}>
              <Typography variant="body1" color="primary">
                {value || "-"}
              </Typography>
            </MuiLink>
          )
        ) : (
          <Typography variant="body1" color="primary">
            {value || "-"}
          </Typography>
        )}
        <Typography variant="caption">{label}</Typography>
      </Stack>
    </Grid>
  );
}
